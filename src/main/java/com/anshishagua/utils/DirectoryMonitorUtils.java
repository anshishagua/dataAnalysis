package com.anshishagua.utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * User: lixiao
 * Date: 2018/5/11
 * Time: 下午3:10
 */

public class DirectoryMonitorUtils {
    public static void main(String [] args) throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();

        Paths.get("/tmp").register(watchService,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_CREATE);

        while (true) {
            WatchKey watchKey = watchService.poll(1, TimeUnit.SECONDS);

            if (watchKey == null) {
                System.out.println("No event");
                Thread.sleep(1000);
                continue;
            }

            List<WatchEvent<?>> events = watchKey.pollEvents();

            for (WatchEvent watchEvent : events) {
                WatchEvent.Kind<?> kind = watchEvent.kind();

                WatchEvent<Path> event = watchEvent;
                Path path = event.context();

                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    System.out.println("create " + path.getFileName());
                } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.println("modify " + path.getFileName());
                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    System.out.println("delete " + path.getFileName());
                }
            }

            watchKey.reset();
        }
    }
}
