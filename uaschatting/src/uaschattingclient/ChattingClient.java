package   uaschattingclient;

 import   java.io.*;

 import   java.net.*;

public   class ChattingClient  implements   Runnable {

// clientClient:   client socket

// os: the output   stream

// is: the input   stream

        static Socket clientSocket = null;

        static PrintStream os = null;

        static BufferedReader is = null;

        static BufferedReader inputLine = null;

        static boolean closed = false;

public static void main(String[] args) {

         int port_number=2224;

         String host="localhost";

         if (args.length < 2)

         {

                 System.out.println("Host="+host+",   port_number="+port_number);

            } else {

                  host=args[0];

                   port_number=Integer.valueOf(args[1]).intValue();

                }

              // Open a socket   on a given host and port

         try {

                 clientSocket = new Socket(host,   port_number);

                 inputLine = new   BufferedReader(new InputStreamReader(System.in));

                 os = new   PrintStream(clientSocket.getOutputStream());

                 is = new BufferedReader(new   InputStreamReader(clientSocket.getInputStream()));

              } catch (UnknownHostException e) {

                          System.err.println("Host   tidak ditemukan "+host);

                 } catch (IOException e) {

                           System.err.println("Tidak   dapat menampilkan I/O "+host);

                 }

              // Open a   connection to on port port_number

           if (clientSocket != null &&   os != null && is != null) {

           try {

                  // Create a thread to read from the server

            new Thread(new   ChattingClient()).start();

            while   (!closed) {

                       os.println(inputLine.readLine()+'\n');

                       }

           // Clean   up:

          //   close the output stream

          //   close the input stream

          //   close the socket

               os.close();

               is.close();

               clientSocket.close();

               } catch (IOException e) {

              System.err.println("IOException:    " + e);

                        }

                  }

               }

public void run() {

            String responseLine;

            try{

                    while ((responseLine = is.readLine()) !=   null) {

                    System.out.println(responseLine);

                    if   (responseLine.indexOf("*** Bye") != -1) break;

                  }

          closed=true;

                      } catch (IOException e) {

             System.err.println("IOException:  " + e);

                 }

           }

 }