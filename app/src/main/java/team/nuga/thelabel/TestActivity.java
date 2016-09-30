package team.nuga.thelabel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by ssuncake 2016-09-30
 * 현재 이 앱은 서버에 ID값을 Integer로 올리고 있기 때문에,
 * 해시맵을 이용하여 string과 int값이 같은 의미를 가지도록 하기 위한 예제
 */
public class TestActivity extends AppCompatActivity {
    public static final String TEST_HASHMAP = "Hash Brown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        //R.array에 저장된값 가져옴
        GenreList = getResources().getStringArray(R.array.genrelist);
    }

    HashMap<String, Integer> genre = new HashMap<>();
    String[] GenreList;

    @OnClick(R.id.printlog_genreId)
    public void onposition() {
        for (int i = 0; i < GenreList.length; i++) {
            genre.put(GenreList[i], i);                                 //  Map에 장르 값 입력
        }
        String genre_pop = GenreList[2];                                //장르 리스트 중 "팝" 저장
        Set<Map.Entry<String, Integer>> set = genre.entrySet();         //해시맵의 키벨류값을 Set에 담아줌.
        Iterator<Map.Entry<String, Integer>> iterator_two = set.iterator(); //set의정보를 이터레이터에 전달해줌.
        while (iterator_two.hasNext()) {
            Map.Entry<String, Integer> gen = iterator_two.next();      //해시맵에 포함된 Key, Value값을 호출함.
            if (genre_pop == gen.getKey()) {                            //  "팝"에  해당하는 Key값 확인
                Toast.makeText(this, "장르 : " + gen.getKey(), Toast.LENGTH_SHORT).show();
            }
            Log.i(TEST_HASHMAP, "key2 : " + gen.getKey() + ", value : " + gen.getValue());  //로그
        }
    }
}
