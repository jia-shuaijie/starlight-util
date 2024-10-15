package org.starlight.util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author 黑色的小火苗
 */
public class FileUtil {
    private static final int BUFFER_SIZE = 8192; // 将缓冲区大小定义为常量

    /**
     * zip解压
     *
     * @param srcFile     zip源文件
     * @param destDirPath 解压后的目标文件夹
     * @throws RuntimeException 解压失败会抛出运行时异常
     */
    public static void unZip(File srcFile, String destDirPath, Charset charset) {
        try (ZipFile zipFile = new ZipFile(srcFile, charset)) {
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.isDirectory()) {
                    createDirectory(destDirPath + File.separator + entry.getName());
                    continue;
                }
                createFile(destDirPath, entry, zipFile);
            }
        } catch (IOException ex) {
            throw new RuntimeException("unzip error from ZipUtils", ex);
        }
    }

    private static void createFile(String destDirPath, ZipEntry entry, ZipFile zipFile) throws IOException {
        File targetFile = new File(destDirPath + "/" + entry.getName());
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        targetFile.createNewFile();

        try (InputStream is = zipFile.getInputStream(entry);
             FileOutputStream fos = new FileOutputStream(targetFile)) {
            byte[] buf = new byte[BUFFER_SIZE];
            int len;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
        }
    }

    /**
     * 压缩文件
     *
     * @param zipPath       压缩包全路径
     * @param fileFullPaths 压缩文件 [HashMap<文件名:文件全路径>]
     */
    public static void toZip(String zipPath, Map<String, String> fileFullPaths) {
        try {
            FileOutputStream outputStream = new FileOutputStream(zipPath);
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
            for (Map.Entry<String, String> entry : fileFullPaths.entrySet()) {
                FileInputStream inputStream = new FileInputStream(entry.getValue());
                zipOutputStream.putNextEntry(new ZipEntry(entry.getKey()));

                int len;
                // 定义每次读取的字节数组
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, len);
                }
                inputStream.close();
            }
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据文件路径删除文件
     * <p>
     * 不支持删除目录
     *
     * @param filePathList 多个文件路径
     */
    public static void deleteFile(List<String> filePathList) {
        for (String path : filePathList) {
            File file = new File(path);
            if (file.isDirectory()) {
                deleteDirectory(file);
                continue;
            }
            if (file.exists() && !file.delete()) {
                throw new RuntimeException("文件删除失败!");
            }
        }
    }

    public static void deleteDirectory(File file) {
        List<File> files = readFilePath(file);
        deleteFile(files.stream().map(File::getAbsolutePath).collect(Collectors.toList()));
        file.delete();
    }

    /**
     * 读取指定目录下的所有文件
     *
     * @param dirPath 目录路径
     * @return 目录下所有文件
     */
    public static List<File> readFilePath(String dirPath) {
        return readFilePath(new File(dirPath));
    }

    /**
     * 读取指定目录下的所有文件
     *
     * @param dir 目录
     * @return 目录下所有文件
     */
    public static List<File> readFilePath(File dir) {
        if (!dir.exists()) {
            throw new IllegalArgumentException("目标目录不存在!");
        }
        Vector<File> allFiles = new Vector<>();
        // 使用队列来模拟迭代过程
        Queue<File> queue = new LinkedList<>();
        queue.offer(dir);

        while (!queue.isEmpty()) {
            File currentDir = queue.poll();
            File[] fileList = currentDir.listFiles();
            if (fileList == null) {
                continue;
            }
            for (File file : fileList) {
                if (file.isDirectory()) {
                    // 将子目录加入队列
                    queue.offer(file);
                    continue;
                }
                allFiles.add(file);
            }
        }
        return allFiles;
    }

    /**
     * 将输入流中的数据写入指定文件。
     *
     * @param targetFile 目标文件
     * @param ins        输入流
     */
    public static void toFile(File targetFile, InputStream ins) {
        try (OutputStream os = Files.newOutputStream(targetFile.toPath())) { // 使用try-with-resources
            int bytesRead;
            byte[] buffer = new byte[BUFFER_SIZE]; // 使用常量定义缓冲区大小
            while ((bytesRead = ins.read(buffer, 0, BUFFER_SIZE)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // 提供更具体的异常信息
            throw new RuntimeException("Failed to write inputStream to file: " + targetFile.getAbsolutePath(), e);
        } finally {
            try {
                ins.close(); // 确保输入流被关闭
            } catch (IOException e) {
                // 记录异常信息
                System.err.println("Failed to close input stream: " + e.getMessage());
            }
        }
    }

    /**
     * 根据路径创建目录
     *
     * @param dir 目录
     */
    public static void createDirectory(String dir) {
        File file = new File(dir);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdir();
        }
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }
}
