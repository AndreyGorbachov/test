package by.test.client.web.mvc;

/**
 * 
 * @author Andrey Gorbachov
 *
 */

public class ResponseForm {

	private Object data;
	private String error;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
