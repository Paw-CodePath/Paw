package com.paw.pawapp.models.utilities;

import java.util.List;

public interface ListObserver<T> {
    void onUpdate(List<T> items);

    void onError(Exception e);
}
