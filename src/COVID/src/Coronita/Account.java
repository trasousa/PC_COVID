package COVID.src.Coronita;

public class Account {
    String id;
    String passwd;
    int cases;


    public void registerClient(String Username, String pass1, String pass2) {
        id = Username;
        if(pass1.equals(pass2)){
            passwd = pass1;
        }
    }

    public String getId() {
        return id;
    }

    public String getPasswd() {
        return passwd;
    }

    public int getCases() {
        return cases;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }
}
