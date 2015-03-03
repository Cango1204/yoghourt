package com.example.hbjia.level2.opengl;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by hbjia on 2015/3/3.
 */
public class MyRender implements GLSurfaceView.Renderer {

    private Square square = new SmoothColoredSquare();
    private float angle;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        gl10.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        gl10.glShadeModel(GL10.GL_SMOOTH);
        gl10.glClearDepthf(1.0f);
        gl10.glEnable(GL10.GL_DEPTH_TEST);
        gl10.glDepthFunc(GL10.GL_LEQUAL);
        gl10.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        gl10.glViewport(0,0,width,height);
        gl10.glMatrixMode(GL10.GL_PROJECTION);
        gl10.glLoadIdentity();
        GLU.gluPerspective(gl10, 45.0f, (float)width/(float)height,
                0.1f, 100.0f);
        gl10.glMatrixMode(GL10.GL_MODELVIEW);
        gl10.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl10.glLoadIdentity();
        gl10.glTranslatef(0, 0, -10);

        gl10.glPushMatrix();;
        gl10.glRotatef(angle, 0, 0, 1);
        square.draw(gl10);
        gl10.glPopMatrix();

        gl10.glPushMatrix();
        gl10.glRotatef(-angle, 0, 0, 1);
        gl10.glTranslatef(2, 0, 0);
        gl10.glScalef(0.5f, 0.5f, 0.5f);
        square.draw(gl10);

        gl10.glPushMatrix();
        gl10.glRotatef(-angle, 0, 0, 1);
        gl10.glTranslatef(2, 0, 0);
        gl10.glScalef(0.5f, 0.5f, 0.5f);
        gl10.glRotatef(angle * 10, 0, 0, 1);
        square.draw(gl10);

        gl10.glPopMatrix();
        gl10.glPopMatrix();
        angle++;
    }
}
