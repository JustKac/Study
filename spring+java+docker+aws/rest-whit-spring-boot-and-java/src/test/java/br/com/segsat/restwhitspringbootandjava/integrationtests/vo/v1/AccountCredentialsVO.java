package br.com.segsat.restwhitspringbootandjava.integrationtests.vo.v1;

import java.io.Serializable;

public class AccountCredentialsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String passowrd;

    public AccountCredentialsVO(String username, String passowrd) {
        this.username = username;
        this.passowrd = passowrd;
    }

    public AccountCredentialsVO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((passowrd == null) ? 0 : passowrd.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccountCredentialsVO other = (AccountCredentialsVO) obj;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        if (passowrd == null) {
            if (other.passowrd != null)
                return false;
        } else if (!passowrd.equals(other.passowrd))
            return false;
        return true;
    }

}
