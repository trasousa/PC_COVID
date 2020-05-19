package COVID.src.Coronita;

public class Account {
    String id;
    String passwd;
    int cases;

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
