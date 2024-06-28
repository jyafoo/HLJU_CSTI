package org.jyafool.os.e2;

import lombok.Data;

/**
 * 进程控制块
 *
 * @author JIA
 * @version 1.0
 * @since 2024/5/27
 */
@Data
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
     * 页表
     */
    private Page pageTable;

    /**
     * 队列指针
     */
    private PCB next;

    public PCB(String id, int length) {
        this.id = id;
        this.length = length;
        pageTable = null;
        this.isBusy = true;
    }

/*    public PCB(String id, int baseAddr, int length,boolean isBusy) {
        this.id = id;
        this.baseAddr = baseAddr;
        this.length = length;
        this.isBusy = isBusy;
    }*/
}
