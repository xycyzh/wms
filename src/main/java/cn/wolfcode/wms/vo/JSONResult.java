package cn.wolfcode.wms.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JSONResult {
    private String msg;
    private boolean success = true;

    public static JSONResult success(String msg) {
        return new JSONResult(msg, true);
    }

    public static JSONResult mark(String msg) {
        return new JSONResult(msg, false);
    }
}
