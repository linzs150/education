package com.one.education.api;

/**
 * The interface Callback.
 *
 * @param <T> the type parameter
 */
public interface ICallback<T> {
    /**
     * On callback.
     *
     * @param objects the objects
     */
    void onCallback(T objects);
}
