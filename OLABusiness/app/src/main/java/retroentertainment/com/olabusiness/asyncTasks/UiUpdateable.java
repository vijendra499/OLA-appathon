package retroentertainment.com.olabusiness.asyncTasks;

import com.application.trotez.data.BaseData;


public interface UiUpdateable<T> {

	void updateUi(BaseData<T> data);
}
