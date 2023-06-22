package tcf.com.tcp.curriculumdesign2.ui.myself;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyselfViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyselfViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}