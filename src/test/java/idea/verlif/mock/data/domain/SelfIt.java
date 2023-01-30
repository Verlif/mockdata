package idea.verlif.mock.data.domain;

import com.alibaba.fastjson2.JSONObject;

/**
 * @author Verlif
 */
public class SelfIt {

    private SelfIt selfOne;

    private SelfIt selfTwo;

    private SelfIt selfThree;

    public SelfIt getSelfOne() {
        return selfOne;
    }

    public void setSelfOne(SelfIt selfOne) {
        this.selfOne = selfOne;
    }

    public SelfIt getSelfTwo() {
        return selfTwo;
    }

    public void setSelfTwo(SelfIt selfTwo) {
        this.selfTwo = selfTwo;
    }

    public SelfIt getSelfThree() {
        return selfThree;
    }

    public void setSelfThree(SelfIt selfThree) {
        this.selfThree = selfThree;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
