package team.nuga.thelabel;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import team.nuga.thelabel.data.CityData;

public class ScrollingActivity extends AppCompatActivity {


    private ListView listView = null;
    private CityAdapter cityAdapter=null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        listView = (ListView)findViewById(R.id.listView_city);
        listView = (ListView) findViewById(R.id.listView_cityTown);
        cityAdapter= new CityAdapter(this);
        listView.setAdapter(cityAdapter);


        addCityItem();
    }


    private class ViewHolder {
        public TextView townId;
        public TextView cityId;
        public TextView cityDetailId;
        public TextView townName;
    }

    private class CityAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<CityData> mListData = new ArrayList<CityData>();

        public CityAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }


        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void add(int townId, int cityId, int cityDetailId, String townName) {
            CityData addInfo = null;
            addInfo = new CityData();
            addInfo.townId = townId;
            addInfo.cityId = cityId;
            addInfo.cityDetailId = cityDetailId;
            addInfo.townName = townName;

            mListData.add(addInfo);
            dataChage();
        }
        public void remove(int position){
            mListData.remove(position);
            dataChage();
        }
        public void dataChage(){
            cityAdapter.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.scrolling_listview_item, null);

                holder.cityDetailId = (TextView) convertView.findViewById(R.id.textView_cityDetail_Id);
                holder.cityId = (TextView) convertView.findViewById(R.id.textView_cityId);
                holder.townId = (TextView) convertView.findViewById(R.id.textView_townId);
                holder.townName = (TextView) convertView.findViewById(R.id.textView_townName);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            CityData mData = mListData.get(position);

            holder.townName.setText(mData.townName);
            holder.townId.setText(" " + mData.townId);
            holder.cityId.setText(", " + mData.cityId);
            holder.cityDetailId.setText(", " + mData.cityDetailId+", ");

            return convertView;
        }
    }

    int townIdCount = 0;

    private void addCityItem() {
        for (int i = 1; i < 19; i++) {
            if (i == 1) {
                for (int j = 1; j < 2; j++) {
                        townIdCount++;
                cityAdapter.add(townIdCount, i, j, Town_Default[j-1]);
                }
            }
            if (i == 2) {
                for (int j = 1; j < 25; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, Seoul[j]);
                }
            }
            if (i == 3) {
                for (int j = 1; j < 46; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, Gyongi[j]);
                }
            }
            if (i == 4) {
                for (int j = 1; j < 19; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, Gangwon[j]);
                }
            }
            if (i == 5) {
                for (int j = 1; j < 11; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, Incheon[j]);
                }
            }
            if (i == 6) {
                for (int j = 1; j < 16; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, ChungBook[j]);
                }
            }
            if (i == 7) {
                for (int j = 1; j < 17; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, ChungNam[j]);
                }
            }
            if (i == 8) {
                for (int j = 1; j < 2; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, Sejong[j]);
                }
            }
            if (i == 9) {
                for (int j = 1; j < 6; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, DaeJeon[j]);
                }
            }
            if (i == 10) {
                for (int j = 1; j < 26; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, GyongBook[j]);
                }
            }
            if (i == 11) {
                for (int j = 1; j < 24; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, GyonNam[j]);
                }
            }
            if (i == 12) {
                for (int j = 1; j < 9; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, DaeGu[j]);
                }
            }
            if (i == 13) {
                for (int j = 1; j < 17; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, Busan[j]);
                }
            }
            if (i == 14) {
                for (int j = 1; j < 6; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, Ulsan[j]);
                }
            }
            if (i == 15) {
                for (int j = 1; j < 17; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, JeonBook[j]);
                }
            }
            if (i == 16) {
                for (int j = 1; j < 23; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, JeonNam[j]);
                }
            }
            if (i == 17) {
                for (int j = 1; j < 6; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, Gwangju[j]);
                }
            }
            if (i == 18) {
                for (int j = 1; j < 3; j++) {
                    townIdCount++;
                    cityAdapter.add(townIdCount, i, j, Jeju[j]);
                }
            }
        }
    }


    public String[] city = {
            "선택하지않음", "서울특별시", "경기도", "강원도", "인천광역시",
            "충청북도", "충청남도", "세종특별시", "대전광역시", "경상북도",
            "경상남도", "대구광역시", "부산광역시", "울산광역시", "전라북도",
            "전라남도", "광주광역시", "제주특별자치도"
    };

    public String[] Town_Default = {
            "선택하지않음"
    };


    // 선택하지않음 = 1
    // 서울시특별시 = 2
    public String[] Seoul = {"선택하지않음",
            "강남구", "강동구", "강서구", "강북구", "관악구",
            "광진구", "구로구", "금천구", "노원구", "도봉구",
            "동대문구", "동작구", "마포구", "서대문구", "서초구",
            "성동구", "성북구", "송파구", "양천구", "영등포구",
            "용산구", "은평구", "중량구", "중구"
    };
    // 경기도 = 3
    public String[] Gyongi = {"선택하지않음",
            "가평군", "고양시 덕양구", "고양시 일산동구", "고양시 일산서구", "과천시",
            "광명시", "광주시", "구리시", "군포시", "김포시",
            "남양주시", "동두천시", "부천시", "부천시 소사구", "부천시 오정구",
            "부천시 원미구", "성남시 분당구", "성남시 수정구", "성남시 중원구", "수원시 권선구",
            "수원시 영통구", "수원시 장안구", "수원시 팔달구", "시흥시", "안산시 단원구",
            "안산시 상록구", "안성시", "안양시 동안구", "안양시 만안구", "양주시",
            "양평군", "여주시", "연천군", "오산시", "용인시 기흥구",
            "용인시 수지구", "용인시 처인구", "의왕시", "의정부시", "이천시",
            "파주시", "평택시", "포천시", "하남시", "화성시",
    };
    //강원도 = 4
    public String[] Gangwon = {"선택하지않음",
            "강릉시", "고성군", "삼척시", "동해시", "속초시",
            "양양군", "양구군", "영월군", "원주시", "인제군",
            "정선군", "철원군", "춘천시", "태백시", "평창군",
            "화천군", "홍천군", "횡성군"
    };
    //인천광역시 = 5
    public String[] Incheon = {"선택하지않음",
            "중구", "동구", "남구", "연수구", "남동구",
            "부평구", "계양구", "서구", "강화군", "옹진군"
    };
    //충청북도 = 6
    public String[] ChungBook = {"선택하지않음",
            "청주시", "청주시 상당구", "청주시 서원구", "청주시 흥덕구", "청주시 청원구",
            "충주시", "제천시", "보은군", "옥천군", "영동군",
            "진천군", "괴산군", "음성군", "단양군", "증평군"
    };
    //충청남도 = 7
    public String[] ChungNam = {"선택하지않음",
            "천안시 동남구", "천안시 서북구", "공주시", "보령시", "아산시",
            "서산시", "논산시", "계룡시", "당진시", "금산군",
            "부여군", "서천군", "청양군", "홍성군", "예산군",
            "태안군"
    };
    //세종특별자치시 = 8
    public String[] Sejong = {"선택하지않음",
            "세종시"
    };
    //대전 = 9
    public String[] DaeJeon = {"선택하지않음",
            "중구", "동구", "서구", "유성구", "대덕구"
    };
    //경상북도 = 10
    public String[] GyongBook = {"선택하지않음",
            "포항시", "포항시 남구", "포항시 북구", "경주시", "김천시",
            "안동시", "구미시", "영주시", "영천시", "상주시",
            "문경시", "경산시", "군위군", "의성군", "청송군",
            "영양군", "영덕군", "청도군", "고령군", "성주군",
            "칠곡군", "예천군", "봉화군", "울진군", "울릉군",
    };
    //경상남도 = 11
    public String[] GyonNam = {"선택하지않음",
            "창원시", "창원시 의창구", "창원시 성산구", "창원시 마산합포구", "창원시 마산회권구",
            "창원시 진해구", "진주시", "통영시", "사천시", "김해시",
            "밀양시", "거제시", "양산시", "의령군", "함안군",
            "창녕군", "고성군", "남해군", "하동군", "산청군",
            "함양군", "거창군", "합천군"
    };
    //대구 = 12
    public String[] DaeGu = {"선택하지않음",
            "중구", "동구", "서구", "남구", "북구",
            "수성구", "달서구", "달성군"
    };
    //부산 = 13
    public String[] Busan = {"선택하지않음",
            "중구", "서구", "동구", "영도구", "부산진구",
            "동래구", "남구", "북구", "해운대구", "사하구",
            "금정구", "강서구", "연제구", "수영구", "사상구",
            "기장군"
    };
    //울산 = 14
    public String[] Ulsan = {"선택하지않음",
            "중구", "남구", "동구", "북구", "울주군"
    };
    //전라북도 = 15
    public String[] JeonBook = {"선택하지않음",
            "전주시", "전주시 완산구", "전주시 덕진구", "군산시", "익산시",
            "정읍시", "남원시", "김제시", "완주군", "진안군",
            "무주군", "장수군", "임실군", "순창군", "고창군",
            "부안군"
    };
    //전라남도 = 16
    public String[] JeonNam = {"선택하지않음",
            "목포시", "여수시", "순천시", "나주시", "광양시",
            "담양군", "곡성군", "구례군", "고흥군", "보성군",
            "화순군", "장흥군", "강진군", "해남군", "영양군",
            "무안군", "함평군", "영광군", "장성군", "완도군",
            "진도군", "신안군"
    };
    //광주 = 17
    public String[] Gwangju = {"선택하지않음",
            "동구", "서구", "남구", "북구", "광산구"
    };
    //제주특별자치도 = 18
    public String[] Jeju = {"선택하지않음",
            "제주시", "서귀포시"
    };

}
