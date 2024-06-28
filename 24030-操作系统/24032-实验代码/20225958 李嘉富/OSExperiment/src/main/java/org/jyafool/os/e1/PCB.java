package org.jyafool.os.e1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 进程控制块pcb
 * @author JIA
 * @version 1.0
 * @since 2024/5/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PCB {

    /**
     * 进程标识
     */
    private String id;

    /**
     * 基址
     */
    private int baseAddr;

    /**
     * 长度
     */
    private int length;

    /**
     * 内存占用状态：true：该进程还存在；false：该进程已经释放
     */
    private boolean isBusy;

    /**
     * 队列指针
     */
    private PCB next;

    public PCB(String id, int length) {
        this.id = id;
        this.length = length;
        this.isBusy = true;
    }

    public PCB(String id, int baseAddr, int length,boolean isBusy) {
        this.id = id;
        this.baseAddr = baseAddr;
        this.length = length;
        this.isBusy = isBusy;
    }

}
