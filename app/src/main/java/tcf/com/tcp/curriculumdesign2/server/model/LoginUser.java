package tcf.com.tcp.curriculumdesign2.server.model;

import androidx.annotation.NonNull;

public class LoginUser extends BaseModel implements Cloneable{
    private String phone;
    private String password;
    public LoginUser(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
    @NonNull
    @Override
    public LoginUser clone()  {
        try {
            LoginUser loginUser= (LoginUser) super.clone();
            return loginUser;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return new LoginUser("","");
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
