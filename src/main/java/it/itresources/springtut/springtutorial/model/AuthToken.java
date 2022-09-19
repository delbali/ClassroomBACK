package it.itresources.springtut.springtutorial.model;

public class AuthToken {
    private String accesstoken;
    private String type = "Bearer";
    private String refreshToken;

    public AuthToken() {
    }

    public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public AuthToken(String token) {
        this.accesstoken = token;
    }


    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public AuthToken(String accesstoken, String refreshToken) {
		super();
		this.accesstoken = accesstoken;
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "AuthToken [accesstoken=" + accesstoken + ", type=" + type + ", refreshToken=" + refreshToken + "]";
	}

    
   }

    


