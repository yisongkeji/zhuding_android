package chat.foreseers.com.foreseers;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.foreseers.com.foreseers.dialog.AddFriendDialog;
import chat.foreseers.com.foreseers.dialog.NoFriendNumberDialog;

public class UserDetailsActivity extends AppCompatActivity {

    @BindView(R.id.goback)
    FrameLayout back;
    @BindView(R.id.img_add_friend)
    ImageView imgAddFriend;
    @BindView(R.id.text_user_details_name)
    TextView textUserDetailsName;
    @BindView(R.id.layout_remark)
    LinearLayout layoutRemark;
    @BindView(R.id.layout_analyze_life_book)
    LinearLayout layoutAnalyzeLifeBook;

    private NoFriendNumberDialog noFriendNumberDialog;
    private AddFriendDialog addFriendDialog;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.goback, R.id.img_add_friend, R.id.layout_remark,R.id.layout_analyze_life_book})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goback:
                finish();
                break;
            case R.id.img_add_friend:
                int i = 1;
                switch (i) {
                    case 0:
                        noFriendNumberDialog = new NoFriendNumberDialog(this, R.style.MyDialog, new
                                NoFriendNumberDialog
                                        .LeaveMyDialogListener() {

                                    @Override
                                    public void onClick(View view) {
                                        noFriendNumberDialog.dismiss();
                                    }
                                });

                        noFriendNumberDialog.setCancelable(true);

//                修改弹窗位置
                        changeDialogLocation(noFriendNumberDialog);

                        noFriendNumberDialog.show();
                        break;

                    case 1:
                        addFriendDialog = new AddFriendDialog(this, R.style.MyDialog, new
                                AddFriendDialog.LeaveMyDialogListener() {

                                    @Override
                                    public void onClick(View view) {
                                        addFriendDialog.dismiss();
                                    }
                                });
                        addFriendDialog.setCancelable(true);

//                修改弹窗位置
                        changeDialogLocation(addFriendDialog);

                        addFriendDialog.show();

                        break;

                    default:
                        break;


                }
                break;


            case R.id.layout_remark:
                intent = new Intent(this, RemarkActivity.class);
                startActivity(intent);
                break;

            case R.id.layout_analyze_life_book:
                intent = new Intent(this, UserAnalyzeLifeBookActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void changeDialogLocation(Dialog dialog) {
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        p.x = 0; //x小于0左移，大于0右移
        p.y = -300; //y小于0上移，大于0下移
        dialogWindow.setAttributes(p);
    }
}
