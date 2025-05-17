package io.github.swnck.request.body.type.formData;

import io.github.swnck.request.body.Body;
import io.github.swnck.util.ContentType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class FormDataBody extends Body {
    private Map<String, Object> formData = new HashMap<>();

    public FormDataBody() {
        super(ContentType.MULTIPART_FORM_DATA);
    }
}
