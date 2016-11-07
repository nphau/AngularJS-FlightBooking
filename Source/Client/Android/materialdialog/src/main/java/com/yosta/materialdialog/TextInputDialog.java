package com.yosta.materialdialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by yarolegovich on 16.04.2016.
 */
public class TextInputDialog extends AbsDialog<TextInputDialog> {

    private static final String KEY_HAS_ERROR = "key_has_error";
    private static final String KEY_TYPED_TEXT = "key_typed_text";

    private EditText inputField;
    private TextView errorMessage;
    private TextView confirmButton;

    private TextFilter filter;

    public TextInputDialog(Context context) {
        super(context);
    }

    public TextInputDialog(Context context, int theme) {
        super(context, theme);
    }

    {
        confirmButton = findView(R.id.ld_btn_confirm);
        inputField = findView(R.id.ld_text_input);
        errorMessage = findView(R.id.ld_error_message);
        inputField.addTextChangedListener(new HideErrorOnTextChanged());
    }

    public TextInputDialog setConfirmButton(@StringRes int text, OnTextInputConfirmListener listener) {
        return setConfirmButton(string(text), listener);
    }

    public TextInputDialog setConfirmButton(String text, OnTextInputConfirmListener listener) {
        confirmButton.setText(text);
        confirmButton.setOnClickListener(new TextInputListener(listener));
        return this;
    }

    public TextInputDialog setConfirmButtonColor(int color) {
        confirmButton.setTextColor(color);
        return this;
    }

    public TextInputDialog setInputFilter(@StringRes int errorMessage, TextFilter filter) {
        return setInputFilter(string(errorMessage), filter);
    }

    public TextInputDialog setInputFilter(String errorMessage, TextFilter filter) {
        this.filter = filter;
        this.errorMessage.setText(errorMessage);
        return this;
    }

    public TextInputDialog setErrorMessageColor(int color) {
        errorMessage.setTextColor(color);
        return this;
    }

    public TextInputDialog setInputType(int inputType) {
        inputField.setInputType(inputType);
        return this;
    }

    public TextInputDialog addTextWatcher(TextWatcher textWatcher) {
        inputField.addTextChangedListener(textWatcher);
        return this;
    }

    public TextInputDialog setInitialInput(@StringRes int text) {
        return setInitialInput(string(text));
    }

    public TextInputDialog setInitialInput(String text) {
        inputField.setText(text);
        return this;
    }

    public TextInputDialog setHint(@StringRes int hint) {
        return setHint(string(hint));
    }

    public TextInputDialog setHint(String text) {
        inputField.setHint(text);
        return this;
    }

    private void setError() {
        errorMessage.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        errorMessage.setVisibility(View.GONE);
    }

    @Override
    void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_HAS_ERROR, errorMessage.getVisibility() == View.VISIBLE);
        outState.putString(KEY_TYPED_TEXT, inputField.getText().toString());
    }

    @Override
    void restoreState(Bundle savedState) {
        super.restoreState(savedState);
        if (savedState.getBoolean(KEY_HAS_ERROR, false)) {
            setError();
        }
        inputField.setText(savedState.getString(KEY_TYPED_TEXT));
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_text_input;
    }

    private class TextInputListener implements View.OnClickListener {

        private OnTextInputConfirmListener wrapped;

        private TextInputListener(OnTextInputConfirmListener wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public void onClick(View v) {
            String text = inputField.getText().toString();

            if (filter != null) {
                boolean isWrongInput = !filter.check(text);
                if (isWrongInput) {
                    setError();
                    return;
                }
            }

            if (wrapped != null) {
                wrapped.onTextInputConfirmed(text);
            }

            dismiss();
        }
    }

    private class HideErrorOnTextChanged implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            hideError();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public interface OnTextInputConfirmListener {
        void onTextInputConfirmed(String text);
    }

    public interface TextFilter {
        boolean check(String text);
    }
}
