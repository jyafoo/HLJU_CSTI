package org.jyafool.os.e2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 页
 *
 * @author JIA
 * @version 1.0
 * @since 2024/5/27
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Page {
    /**
     * 块号
     */
    private int blockNo;

    /**
     * 状态位
     */
    private int sign;

    /**
     * 页指针
     */
    private Page next;
}
