package org.jyafool.os.e3;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * 文件控制块
 *
 * @Author jyafool
 * @Version 1.0
 * @Since 2024/6/13
 */
@Data
public class FCB {
    /**
     * 主FCB（文件控制块）对象，用于系统中的主要文件管理。
     */
    public static final FCB mainFCB = new FCB();

    /**
     * 目录或文件名
     */
    private final String name;

    /**
     * 文件或目录大小，目录大小可设置为0
     */
    private int size;

    /**
     * 日期时间
     */
    private final Date date;

    /**
     * 日期格式
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * 记录该文件或目录所在fat中的第一块块号
     */
    private int firstBlock;

    /**
     * 目录列表
     */
    private final LinkedList<FCB> catalogueList = new LinkedList<>();

    /**
     * 文件列表
     */
    private final LinkedList<FCB> fileList = new LinkedList<>();

    public FCB() {
        this.date = new Date();
        this.name = "root";
        addCatalogue(".", 0);
        addCatalogue("..", 0);
    }

    public FCB(String name, int size, int firstBlock) {
        this.date = new Date();
        this.name = name;
        this.size = size;
        this.firstBlock = firstBlock;
    }

    public void addCatalogue(String name, int firstBlock) {
        for (FCB i : catalogueList) {
            if (i.name.equals(name)) {
                System.out.println("目录名重复！");
                return;
            }
        }
        catalogueList.addLast(new FCB(name, OS.BLOCK_SIZE, firstBlock));
    }

    /**
     * 在目录列表中查找指定名称的FCB（文件控制块）。
     *
     * @param name 要查找的FCB的名称。
     * @return 如果找到匹配的FCB，则返回该FCB对象；如果未找到，则返回null。
     */
    public FCB findCatalogue(String name) {
        for (FCB fcb : catalogueList) {
            if (fcb.name.equals(name)) {
                return fcb;
            }
        }
        return null;
    }

    /**
     * 删除目录并返回其首个块的位置
     *
     * @param name 目录的名称。
     * @return 如果成功删除了目录且目录为空，返回该目录的首个块的位置；否则返回-1。
     */
    public int removeCatalogue(String name) {
        // 初始化标志变量f为-1，用于后续表示是否找到要删除的目录以及是否成功删除
        int f = -1;
        for (int i = 0; i < catalogueList.size(); i++) {
            if (catalogueList.get(i).name.equals(name)) {
                // 记录该目录的首个块的位置
                f = catalogueList.get(i).firstBlock;
                // 检查目录下的子目录或文件是否为空，如果不为空，则不能删除
                if (!catalogueList.get(i).catalogueList.isEmpty() ||
                        !catalogueList.get(i).fileList.isEmpty()) {
                    // 如果目录不为空，设置f为-1，表示删除失败
                    f = -1;
                    System.out.println("目录不为空，删除失败。");
                    break;
                }
            }
        }
        // 使用Lambda表达式移除名称匹配的目录，如果成功移除，则返回首个块的位置，否则返回-1
        if (catalogueList.removeIf(i -> i.name.equals(name))) {
            return f;
        } else {
            return -1;
        }
    }

    /**
     * 添加文件到文件列表。
     *
     * @param name 文件名，用于识别文件。
     * @param size 文件大小，以字节为单位。
     * @param firstBlock 文件的第一个数据块号，用于定位文件在存储设备上的位置。
     */
    public void addFile(String name, int size, int firstBlock) {
        for (FCB i : fileList) {
            if (i.name.equals(name)) {
                System.out.println("文件名重复！");
                return;
            }
        }
        fileList.addLast(new FCB(name, size, firstBlock));
    }

    /**
     * 删除指定名称的文件
     *
     * 遍历文件列表，寻找名称匹配的文件。
     *      如果找到，它会记录该文件的第一个块的位置，
     *          然后从文件列表中移除该文件：
     *              如果移除成功，说明文件确实存在且已被删除，方法将返回该文件的第一个块的位置
     *              如果移除失败，说明文件不存在，方法将返回-1
     *
     * @param name 要删除的文件的名称。
     * @return 如果文件删除成功，返回该文件的第一个块的位置；如果文件不存在或删除失败，返回-1。
     */
    public int removeFile(String name) {
        int f = -1;
        for (int i = 0; i < fileList.size(); i++) {
            if (fileList.get(i).name.equals(name)) {
                f = fileList.get(i).firstBlock;
                break;
            }
        }
        if (fileList.removeIf(i -> i.name.equals(name))) {
            return f;
        } else {
            return -1;
        }
    }

    /**
     * 展示当前FCB下的文件和目录信息
     */
    public void show() {
        for (FCB i : catalogueList) {
            System.out.println(sdf.format(i.date) + "\t<DIR>\t" + i.name + "\t目录");
        }
        for (FCB i : fileList) {
            System.out.println(sdf.format(i.date) + "\t<DIR>\t" + i.name + "\t文件");
        }
        System.out.println("总计：\t" + catalogueList.size() + "个目录\t" + fileList.size() + "个文件");
    }

    /**
     * 展示文件控制块（FCB）信息
     *
     * @param fcb 要展示的文件控制块，包含目录和文件的信息。
     */
    public void showFCB(FCB fcb) {
        for (FCB i : fcb.catalogueList) {
            showFCB(i);
        }
        for (FCB i : fcb.catalogueList) {
            System.out.println(sdf.format(i.date) + "\t<DIR>\t" + i.name + "\t目录");
        }
        for (FCB i : fcb.fileList) {
            System.out.println(sdf.format(i.date) + "\t<DIR>\t" + i.name + "\t文件");
        }
    }

}
