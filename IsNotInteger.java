//https://stackoverflow.com/questions/9814283/need-to-create-a-new-runtimeexception-for-emptystacks

public class IsNotInteger extends RuntimeException {

public IsNotInteger() {
        super();
}
public IsNotInteger(String s) {
        super(s);
}

}
