package com.example.home.swipetab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

public class IntroGroupView extends ViewGroup {
    private static final String TAG = "SlidingView";

    // 드래그 속도와 방향을 판단하는 클래스
    private VelocityTracker mVelocityTracker = null;

    // 화면 전환을 위한 드래그 속도의 최소값 pixel/s (100 정도으로 속도로 이동하면 화면전환으로 인식)
    private static final int SNAP_VELOCITY = 100;

    /* 화면에 대한 터치이벤트가 화면전환을 위한 터치인가? 현 화면의 위젯동작을 위한
        터치인가? 구분하는 값 (누른상태에서 10px 이동하면 화면 이동으로 인식) */
    private int mTouchSlop = 10;

    private Bitmap mWallpaper = null; // 배경화면을 위한 비트맵
    private Paint mPaint = null;

    /* 화면 자동 전황을 위한 핵심 클래스 ( 화면 드래그후 손을 뗏을때
        화면 전환이나 원래 화면으로 자동으로 스크롤 되는 동작을 구현하는 클래스) */
    private Scroller mScroller = null;
    private PointF mLastPoint = null; // 마지막 터치 지점을 저장하는 클래스
    private int mCurPage = 0; // 현재 화면 페이지

    private int mCurTouchState; // 현재 터치의 상태
    private static final int TOUCH_STATE_SCROLLING = 0; // 현재 스크롤 중이라는 상태
    private static final int TOUCH_STATE_NORMAL = 1; // 현재 스크롤 상태가 아님

    private Toast mToast;

    public IntroGroupView(Context context) {
        super(context);
        init();
    }

    public IntroGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IntroGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.d(TAG,"init");
        mPaint = new Paint();
        mScroller = new Scroller(getContext()); // 스크롤러 클래스 생성
        mLastPoint = new PointF();
    }

    // 차일드뷰의 크기를 지정하는 콜백 메서드
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure");
        for (int i = 0; i < getChildCount(); i++) {
            // 각 차일드뷰의 크기는 동일하게 설정
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout");
        for(int i=0; i<getChildCount(); i++){
            int one_child_width =  getChildAt(i).getMeasuredWidth();// Child 하나의 넓이
            int one_child_height = getChildAt(i).getMeasuredHeight(); // Child 하나의 높이
            int child_left = getChildAt(i).getMeasuredWidth() * i; // 왼쪽에올 child의 너비
            getChildAt(i).layout(child_left, t, one_child_width*i + one_child_width, one_child_height);
        }
    }

    // 화면에 VIEW들을 그린다.
    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d(TAG, "dispatchDraw");
        for (int i = 0; i < getChildCount(); i++) {
            drawChild(canvas, getChildAt(i), 100); // 차일드 뷰들을 하나하나 그린다.
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.d(TAG,"Touch this");
        if (mVelocityTracker == null)
            mVelocityTracker = VelocityTracker.obtain();

        // 터치되는 모든 좌표들을 저장하여, 터치 드래그 속도를 판단하는 기초를 만듬
        mVelocityTracker.addMovement(event);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                // 현재 화면이 자동 스크롤 중이라면 (ACTION_UP 의 mScroller 부분 참조)
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation(); // 자동스크롤 중지하고 터치 지점에 멈춰서잇을것
                }
                mLastPoint.set(event.getX(), event.getY()); // 터치지점 저장
                break;

            case MotionEvent.ACTION_MOVE:
                // 이전 터치지점과 현재 터치지점의 차이를 구해서 화면 스크롤 하는데 이용
                int x = (int) (event.getX() - mLastPoint.x);
                scrollBy(-x, 0); // 차이만큼 화면 스크롤
                invalidate(); // 다시 그리기
                mLastPoint.set(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_UP:
                // pixel/ms 단위로 드래그 속도를 구할것인가 지정 (1초로 지정)
                // onInterceptTouchEvent 메서드에서 터치지점을 저장해둔 것을 토대로 한다.
                mVelocityTracker.computeCurrentVelocity(1000);
                int v = (int) mVelocityTracker.getXVelocity(); // x 축 이동 속도를 구함

                int gap = getScrollX() - mCurPage * getWidth(); // 드래그 이동 거리 체크
                Log.d(TAG, "mVelocityTracker : " + v);
                int nextPage = mCurPage;

                // 드래그 속도가 SNAP_VELOCITY 보다 높거니 화면 반이상 드래그 했으면
                // 화면전환 할것이라고 nextPage 변수를 통해 저장.
                if ((v > SNAP_VELOCITY || gap < -getWidth() / 2) && mCurPage > 0) {
                    nextPage--;
                } else if ((v < -SNAP_VELOCITY || gap > getWidth() / 2)
                        && mCurPage < getChildCount() - 1) {
                    nextPage++;
                }

                int move = 0;
                if (mCurPage != nextPage) { // 화면 전환 스크롤 계산
                    // 현재 스크롤 지점에서 화면전환을 위해 이동해야하는 지점과의 거리 계산
                    move = getChildAt(0).getWidth() * nextPage - getScrollX();
                } else { // 원래 화면 복귀 스크롤 계산
                    // 화면 전환 하지 않을것이며 원래 페이지로 돌아가기 위한 이동해야하는 거리 계산
                    move = getWidth() * mCurPage - getScrollX();
                }

                // 핵심!! 현재 스크롤 지점과 이동하고자 하는 최종 목표 스크롤 지점을 설정하는 메서드
                // 현재 지점에서 목표 지점까지 스크롤로 이동하기 위한 중간값들을 자동으로 구해준다.
                // 마지막 인자는 목표 지점까지 스크롤 되는 시간을 지정하는 것. 밀리세컨드 단위이다.
                // 마지막 인자의 시간동안 중간 스크롤 값들을 얻어 화면에 계속 스크롤을 해준다.
                // 그러면 스크롤 애니메이션이 되는것처럼 보인다. (computeScroll() 참조)
                mScroller.startScroll(getScrollX(), 0, move, 0, Math.abs(move));

                if (mToast != null) {
                    mToast.setText("page : " + nextPage);
                } else {
                    mToast = Toast.makeText(getContext(), "page : " + nextPage,
                            Toast.LENGTH_SHORT);
                }
                mToast.show();
                invalidate();
                mCurPage = nextPage;

                // 터치가 끝났으니 저장해두었던 터치 정보들 삭제하고
                // 터치상태는 일반으로 변경
                mCurTouchState = TOUCH_STATE_NORMAL;
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                break;
        }

        return true;
    }


}
