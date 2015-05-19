
package   uaschattingserver;

  import   java.io.*;

  import   java.net.*;

public   class ChattingServer {

               static    Socket clientSocket = null;

               static    ServerSocket serverSocket = null;

               static    clientThread t[] = new clientThread[10];

public static void main(String args[]) {

                BufferedReader br = null;

                PrintStream ps = null;

                String line;

                String nama;

                int port_number=2224;

            if (args.length < 1)

            {

                   System.out.println("Port   number="+port_number);

            } else {

                     port_number=Integer.valueOf(args[0]).intValue();

              }

           try {

                   serverSocket = new   ServerSocket(port_number);

           } catch (IOException e)

                         {System.out.println(e);}

          while(true){

                try {

                      clientSocket =   serverSocket.accept();

                      for(int i=0;   i<=9; i++){

                      if(t[i]==null)

                       {

                       br = new   BufferedReader( new InputStreamReader(clientSocket.getInputStream()));

                       ps = new   PrintStream(clientSocket.getOutputStream());

                       ps.println("Masukan   Nama Lengkap Kamu :");

                       nama = br.readLine();

                       (t[i] = new   clientThread(clientSocket,t)).start();

               // System.out.println(t[i].getName());

                       System.out.println(""+nama+"");

                  break;

                           }

                  }

               }

                   catch (IOException e) {

                                System.out.println(e);}

                      }

                 }

           }

class   clientThread extends Thread{

                 BufferedReader is = null;

                 PrintStream os = null;

                 Socket clientSocket = null;

                 clientThread t[];

                 String name;

                public clientThread(Socket clientSocket,   clientThread[] t){

                   this.clientSocket=clientSocket;

                   this.t=t;

                 }

         public String getNama(){

            return name;

          }

         public void run()

          {

                   String line;

                 //String name;

             try{

                 is = new BufferedReader(new   InputStreamReader(clientSocket.getInputStream()));

                 os = new   PrintStream(clientSocket.getOutputStream());

                 os.println("Masukan Nama panggilan   Kamu:");

                 name = is.readLine();

                 os.println("hai"   + name +      "Selamat Datang.\nEnter /quit   untuk keluar");

                for(int i=0; i<=9; i++)

                       if (t[i]!=null   && t[i]!=this)

                      t[i].os.println("*** User   "+name+" sudah masuk ke chat room ini !!! ***" );

                while (true) {

                        line = is.readLine();

                        if(line.startsWith("/quit")) break;

            //batas   private

                     if   (line.startsWith("+")){

                     java.util.StringTokenizer   ts = new java.util.StringTokenizer(line, ":");

                     String users=   ts.nextToken();

                     String user =   users.substring(1, users.length());

                     String   pesan=ts.nextToken();

                     for(int   i=0; i<=9; i++)

                           if   (t[i]!=null &&    t[i].getNama().equalsIgnoreCase(user))

                            t[i].os.println(pesan);

              //   private

                  } else {

                           for(int i=0;   i<=9; i++)

                          if (t[i]!=null)    t[i].os.println("<"+name+"> "+line);

                           }

                   }

                     for(int i=0; i<=9; i++)

                          if (t[i]!=null   && t[i]!=this)

                          t[i].os.println("*** User   "+name+" meninggalkan chat room !!! ***" );

                          os.println("*** Bye   "+name+" ***");

                 for(int i=0; i<=9; i++)

                         if   (t[i]==this) t[i]=null;

            is.close();

            os.close();

            clientSocket.close();

                  }

         catch(IOException e){};

       }
         
}