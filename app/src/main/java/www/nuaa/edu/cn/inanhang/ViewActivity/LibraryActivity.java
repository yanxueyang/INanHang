package www.nuaa.edu.cn.inanhang.ViewActivity;

import android.os.Bundle;

import butterknife.Bind;
import www.nuaa.edu.cn.inanhang.Base.BaseToolbarActivity;
import www.nuaa.edu.cn.inanhang.R;
import www.nuaa.edu.cn.inanhang.View.LibrarySingelBook;

/**
 * Created by yxy on 15/10/27.
 */
public class LibraryActivity extends BaseToolbarActivity{
    @Bind(R.id.libraryshijing)LibrarySingelBook libraryshijing;
    @Bind(R.id.librarydashi)LibrarySingelBook librarydashi;
    @Bind(R.id.libraryniumeng)LibrarySingelBook libraryniumeng;
    @Bind(R.id.libraryxiyouji)LibrarySingelBook libraryxiyouji;
    @Bind(R.id.librarylubinxun)LibrarySingelBook librarylubinxun;
    @Bind(R.id.librarykong)LibrarySingelBook librarykong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        setToolbarTitle("我的图书馆");
        libraryshijing.set("还剩100天", "诗经", R.mipmap.book_shijing,0);
        librarydashi.set("还剩90天", "大师和玛格丽特", R.mipmap.book_dashi,0);
        libraryniumeng.set("还剩30天","牛虻", R.mipmap.book_niumeng,0);
        libraryxiyouji.set("已过期","西游记", R.mipmap.book_xiyouji,1);
        librarylubinxun.set("已过期","鲁滨逊漂流记", R.mipmap.book_default,1);
        librarykong.hide();
    }
}
