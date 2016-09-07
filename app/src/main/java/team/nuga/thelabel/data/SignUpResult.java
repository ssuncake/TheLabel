package team.nuga.thelabel.data;

/**
 * Created by Blissun on 2016-09-06.
 */
public class SignUpResult {
    ErrorMessage errorMessage;

    public int getId() {
        return id;
    }

    public ErrorMessage getErrorMessage() {

        return errorMessage;
    }

    String email;
    String password;
    String nickname;
    String image;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    String message;
    int id;
    String text;
    int gender;
    int position_id;
    int genre_id;
    int city_id;
    int town_id;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public void setTown_id(int town_id) {
        this.town_id = town_id;
    }

    public String getEmail() {
        return email;

    }

    public String getPassword() {
        return password;
    }

    public int getGender() {
        return gender;
    }

    public int getPosition_id() {
        return position_id;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public int getTown_id() {
        return town_id;
    }
}
