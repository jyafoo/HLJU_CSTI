package org.jyafool.os.e1;

/**
 * 内存
 *
 * @author JIA
 * @version 1.0
 * @since 2024/5/9
 */

public class Memory {
    /**
     * PCB头指针：系统进程
     */
    private final PCB dummy;

    /**
     * 内存初始化
     *
     * @param baseAddr 内存基址，即头指针pcb基址
     * @param capacity 内存最大容量
     */
    public Memory(int baseAddr, int capacity) {
        dummy = new PCB("系统进程", baseAddr, 0, true);
        dummy.setNext(new PCB(null, baseAddr, capacity, false));
    }

    /**
     * 往内存中添加pcb：
     * 遍历内存中的进程控制块链表，寻找第一个足够大的空闲空间来容纳新的pcb。
     * 如果找到，将新的pcb添加到内存中；如果没有足够的空间，则不添加。
     *
     * @param pcb 进程控制块
     * @return true：添加成功；false：添加失败
     */
    public boolean add(PCB pcb) {
        if (pcb == null) {
            System.out.println("无效pcb");
            return false;
        }

        PCB p = dummy, pr = dummy.getNext();
        PCB m = p, freeMr = null;

        // 先检查dummy的下一个节点是否足够大
        /*if (pr != null && !pr.isBusy() && pr.getLength() >= pcb.getLength()) {
            freeMr = pr;
        }*/

        // 遍历内存链表，寻找最合适的空闲内存块
        while (p.getNext() != null) {
            if (!pr.isBusy() && pr.getLength() >= pcb.getLength()) {
                if (freeMr == null || pr.getLength() < freeMr.getLength()) {
                    m = p;
                    freeMr = pr;
                    break;
                }
            }
            p = pr;
            pr = pr.getNext();
        }

        if (freeMr != null) {
            pcb.setBaseAddr(freeMr.getBaseAddr());
            freeMr.setLength(freeMr.getLength() - pcb.getLength());

            if (freeMr.getLength() <= 2) {
                pcb.setLength(pcb.getLength() + freeMr.getLength());
                pcb.setNext(freeMr.getNext());
            } else {
                freeMr.setBaseAddr(pcb.getBaseAddr() + pcb.getLength());
                pcb.setNext(freeMr);
            }

            m.setNext(pcb);
            System.out.println("进程" + pcb.getId() + "已加入到内存中");
            return true;
        } else {
            System.out.println("内存不足！(无空闲内存)");
            return false;
        }
    }


    /**
     * 将pcb从内存中移除
     *
     * @param pcb 进程控制块
     * @return true：移除成功；false：移除失败
     */
    public boolean remove(PCB pcb) {
        if (pcb == null || dummy.getNext() == null) {
            System.out.println("内存无此进程");
            return false;
        }

        PCB p = dummy, pr = dummy.getNext();
        while (p.getNext() != null) {
            if (pcb.getId().equals(pr.getId())) {
                if (pr.getNext() != null && !pr.getNext().isBusy()) {
                    pr.setLength(pr.getNext().getLength() + pr.getLength());
                    pr.setNext(pr.getNext().getNext());
                    pr.setBusy(false);
                }
                if (!p.isBusy()) {
                    p.setLength(p.getLength() + pr.getLength());
                    p.setNext(pr.getNext());
                }
                pcb.setBusy(false);
                System.out.println("进程" + pcb.getId() + "已从内存中移除");
                pr.setId(null);
                return true;
            }
            p = pr;
            pr = pr.getNext();
        }

        System.out.println("无进程" + pcb.getId());
        return false;
    }


    /**
     * 展示内存信息
     */
    public void show() {
        PCB p = dummy.getNext();
        System.out.println("说明 : 进程标识<起始地址，结束地址>  (空闲块)");
        System.out.println("      (" + dummy.getId() + "<" + dummy.getBaseAddr() + "," + (dummy.getBaseAddr() + dummy.getLength()) + ">,长度" + dummy.getLength() + ") ");
        while (p != null) {
            if (!p.isBusy()) {
                System.out.println("      (" + p.getLength() + ") ");
            } else {
                System.out.println("      (" + p.getId() + "<" + p.getBaseAddr() + "," + (p.getBaseAddr() + p.getLength()) + ">,长度" + p.getLength() + ") ");
            }
            p = p.getNext();
        }
        System.out.println();
        p = dummy.getNext();
        while (p != null) {
            if (!p.isBusy()) {
                System.out.println("空闲空间:" + p.getBaseAddr() + "---" + (p.getBaseAddr() + p.getLength()));
            }
            p = p.getNext();
        }
        System.out.println();
    }
}
