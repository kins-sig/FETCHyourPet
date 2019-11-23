package com.sigm.fetchyourpet;

public class DropboxConnection {
    private static final String ACCESS_TOKEN = "zQ0PTVsAoiAAAAAAAAAADljlxwYbAm1tvlgeZHccJkJ0rosm5RtqsyuE6nYkli-P";

    public DropboxConnection() {

    }

    public static void main(String[] args) {
//        System.out.println("Hi");
//
//        try {
//
//            DbxRequestConfig config;
//            config = new DbxRequestConfig("dropbox/FETCH!yourPet", "en");
//            DbxClientV2 client;
//            client = new DbxClientV2(config, ACCESS_TOKEN);
//            FullAccount account;
//            DbxUserUsersRequests r1 = client.users();
//            account = r1.getCurrentAccount();
//            System.out.println(account.getName().getDisplayName());
//
//            // Get files and folder metadata from Dropbox root directory
//            ListFolderResult result = client.files().listFolder("");
//            while (true) {
//                for (Metadata metadata : result.getEntries()) {
//                    Log.d("test",metadata.getPathLower());
//                }
//
//                if (!result.getHasMore()) {
//                    break;
//                }
//
//                result = client.files().listFolderContinue(result.getCursor());
//            }
//
//        } catch (DbxException ex1) {
//            ex1.printStackTrace();
//        }

        //        // Upload "test.txt" to Dropbox
        //        try (InputStream in = new FileInputStream("test.txt")) {
        //            FileMetadata metadata = client.files().uploadBuilder("/test.txt")
        //                .uploadAndFinish(in);
        //        }
        //
        //        DbxDownloader<FileMetadata> downloader = client.files().download("/test.txt");
        //        try {
        //            FileOutputStream out = new FileOutputStream("test.txt");
        //            downloader.download(out);
        //            out.close();
        //        } catch (DbxException ex) {
        //            System.out.println(ex.getMessage());
        //        }
    }
}
