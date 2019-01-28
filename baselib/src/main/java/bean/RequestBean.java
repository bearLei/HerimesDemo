package bean;

/**
 * create by lei on 2019/1/28/028
 * desc:
 */
public class RequestBean {
    private int type;
    private String className;
    private String methodName;
    private RequestParameter[] requestParameters;

    public RequestBean() {
    }

    public RequestBean(int type, String className, String methodName, RequestParameter[] requestParameters) {
        this.type = type;
        this.className = className;
        this.methodName = methodName;
        this.requestParameters = requestParameters;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public RequestParameter[] getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(RequestParameter[] requestParameters) {
        this.requestParameters = requestParameters;
    }
}
