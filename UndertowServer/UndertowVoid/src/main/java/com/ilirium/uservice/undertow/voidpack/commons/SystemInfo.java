package com.ilirium.uservice.undertow.voidpack.commons;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SystemInfo {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    public static final long START_TIME = System.currentTimeMillis();

    public static Map<String, Object> dump() {
        Map<String, Object> dump = new HashMap<>();
        dump.put("time", getTime());
        dump.put("os", osInfo());
        dump.put("memory", memoryInfo());
        dump.put("disk", diskInfo());
        dump.put("jar", jarInfo());
        return dump;
    }

    public static Map<String, Object> getTime() {
        Map<String, Object> info = new HashMap<>();
        final long currentTimeMillis = System.currentTimeMillis();
        info.put("start-time", SIMPLE_DATE_FORMAT.format(new Date(START_TIME)));
        info.put("current-time", SIMPLE_DATE_FORMAT.format(new Date(currentTimeMillis)));
        info.put("up-time", formatInterval(currentTimeMillis - START_TIME));
        return info;
    }

    public static Map<String, Object> jarInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("Manifest Build-Date", ManifestReader.buildDate());
        return info;
    }

    public static Map<String, Object> diskInfo() {
        Map<String, Object> disks = new HashMap<>();
        File[] roots = File.listRoots();
        for (File root : roots) {
            Map<String, Object> disk = new HashMap<>();
            disk.put("AbsolutePath", root.getAbsolutePath());
            disk.put("TotalSpace", root.getTotalSpace());
            disk.put("FreeSpace", root.getFreeSpace());
            disk.put("UsableSpace", root.getUsableSpace());
            disks.put(root.getName(), disk);
        }
        return disks;
    }

    public static Map<String, Object> memoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        NumberFormat format = NumberFormat.getInstance();
        Map<String, Object> memory = new HashMap<>();
        memory.put("free_memory", format.format(runtime.freeMemory() / 1024));
        memory.put("allocated_memory", format.format(runtime.totalMemory() / 1024));
        memory.put("max_memory", format.format(runtime.maxMemory() / 1024));
        memory.put("total_free_memory", format.format((runtime.freeMemory() + (runtime.maxMemory() - runtime.totalMemory())) / 1024));
        memory.put("used_memory", format.format((runtime.totalMemory() - runtime.freeMemory()) / 1024));
        return memory;
    }

    public static Map<String, Object> osInfo() {
        Runtime runtime = Runtime.getRuntime();
        Map<String, Object> os = new HashMap<>();
        os.put("os_name", System.getProperty("os.name"));
        os.put("os_version", System.getProperty("os.version"));
        os.put("os_arch", System.getProperty("os.arch"));
        os.put("availableProcessors", String.valueOf(runtime.availableProcessors()));
        return os;
    }

    public static String formatInterval(final long l) {
        final long hr = TimeUnit.MILLISECONDS.toHours(l);
        final long min = TimeUnit.MILLISECONDS.toMinutes(l - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        final long ms = TimeUnit.MILLISECONDS.toMillis(l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));
        return String.format("%02d:%02d:%02d:%03d", hr, min, sec, ms);
    }
}
