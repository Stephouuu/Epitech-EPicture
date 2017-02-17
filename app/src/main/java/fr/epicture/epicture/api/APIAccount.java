package fr.epicture.epicture.api;

public abstract class APIAccount {

    // ========================================================================
    // FIELDS
    // ========================================================================

    public String id;
    public String username;
    public String profilePic;

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    protected APIAccount() {
        id = "undefined";
        username = "undefined";
        this.profilePic = "";
    }

    public APIAccount(String accountId, String username) throws InstantiationException {
        if (accountId == null || username == null)
            throw new InstantiationException();
        this.id = accountId;
        this.username = username;
        this.profilePic = "";
    }

    // ========================================================================
    // METHODS
    // ========================================================================

    /*public String getAccountId() {
        return accountId;
    }

    public String getUsername() {
        return username;
    }

    protected void setAccountId(String id) {
        accountId = id;
    }

    protected void setUsername(String username) {
        this.username = username;
    }*/

    @Override
    public boolean equals(Object o) {
        /*if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        APIAccount that = (APIAccount) o;
        return accountId == that.accountId;*/

        return o instanceof APIAccount
                && ((APIAccount)o).id.equals(id)
                && ((APIAccount)o).username.equals(username);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

