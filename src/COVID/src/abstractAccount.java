package COVID.src;

public abstract class abstractAccount {

    String passwd;
    int cases;

    public abstractAccount(String passwd,int cases){
        this.passwd=passwd;
        this.cases = cases;
    }
    public abstractAccount(){

    }

    public String getPasswd() {
        return passwd;
    }

    public int getCases() {
        return cases;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }
}
