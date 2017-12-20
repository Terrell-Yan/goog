package com.example.goog.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goog.R;

public abstract class BaseFragment extends Fragment {
	public BaseActivity act;
	public View layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (layout == null) {
			layout = initLayout(inflater);
		}
		return layout;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		act = (BaseActivity) activity;
	}

	/**
	 * ���ྭ���˷������е�ǰFragment���ֵ����
	 * 
	 * @param inflater
	 */
	public abstract View initLayout(LayoutInflater inflater);

	public void startActivity(Class<?> cls) {
		// TODO Auto-generated method stub
		startActivity(new Intent(act, cls));
	}

	public String gettvText(TextView et) {

		return et.getText().toString().trim();

	}

	/**
	 * ����toast 3��
	 * 
	 * @param text
	 *            �������ı�
	 */
	public void toastS(String text) {

		Toast.makeText(act, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * ����Id toast�Զ���ͼƬ 3��
	 * 
	 * @param resId
	 *            ͼƬ��Id
	 */
	public void toastS(int resId) {

		Toast t = new Toast(act);
		ImageView img = new ImageView(act);
		// ���ÿ�͸�
		img.setLayoutParams(new LayoutParams(100, 100));
		// ������Դ
		img.setImageResource(resId);
		// ��ͼƬ����t������
		t.setView(img);
		// ����λ��
		t.setGravity(Gravity.CENTER, 0, 0);
		t.show();
	}

	/**
	 * ��ѯImageView ͨ��Id
	 * 
	 * @param id
	 *            ImageView��id
	 * @return ���ImageView�Ķ���,�����ѯʧ���򷵻�null
	 */

	public ImageView imgById(int id) {

		return (ImageView) findViewById(id);
	}

	/**
	 * Fragment��findViewById����
	 * 
	 * @param id
	 *            ����View���id
	 * @return
	 */
	public View findViewById(int id) {
		// TODO Auto-generated method stub
		return (View) getView().findViewById(id);
	}

	/**
	 * ��ѯTextView ͨ��Id
	 * 
	 * @param id
	 *            TextView��id
	 * @return ���TextView�Ķ���,�����ѯʧ���򷵻�null
	 */

	public TextView tvById(int id) {

		return (TextView) findViewById(id);
	}

	/**
	 * ��ѯButton ͨ��Id
	 * 
	 * @param id
	 *            Button��id
	 * @return ���Button�Ķ���,�����ѯʧ���򷵻�null
	 */

	public Button butById(int id) {

		return (Button) findViewById(id);
	}

	/**
	 * ��ѯEditText ͨ��Id
	 * 
	 * @param id
	 *            EditText��id
	 * @return ���EditText�Ķ���,�����ѯʧ���򷵻�null
	 */

	public EditText etById(int id) {

		return (EditText) findViewById(id);
	}

	/**
	 * ��ѯLinearLayout ͨ��Id
	 * 
	 * @param id
	 *            LinearLayout��id
	 * @return ���LinearLayout�Ķ���,�����ѯʧ���򷵻�null
	 */

	public LinearLayout linearById(int id) {

		return (LinearLayout) findViewById(id);
	}
	/**
	 * 公共方法： 从碎片fragment1跳转到碎片fragment2
	 *
	 * @param fragment1
	 *            当前fragment
	 * @param fragment2
	 *            跳转后的fragment
	 */
	public void showFragment(Fragment fragment1, Fragment fragment2,int id) {
		// 获取 FragmentTransaction  对象
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		//如果fragment2没有被添加过，就添加它替换当前的fragment1
		if (!fragment2.isAdded()) {
			transaction.add(id,fragment2)
					//加入返回栈，这样你点击返回键的时候就会回退到fragment1了
					.addToBackStack(null)
					// 提交事务
					.commitAllowingStateLoss();

		} else { //如果已经添加过了的话就隐藏fragment1，显示fragment2
			transaction
					// 隐藏fragment1，即当前碎片
					.hide(fragment1)
					// 显示已经添加过的碎片，即fragment2
					.show(fragment2)
					// 加入返回栈
					.addToBackStack(null)
					// 提交事务
					.commitAllowingStateLoss();
		}
	}
}
