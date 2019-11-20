package com.example.demo.utils.framework.exection;



public class Exceptions {

	public static void handle(String message) throws Exception {
        throw new DefaultException(message);
    }
    public static void handle( Exception e ) throws Exception {
        throw new DefaultException( e );
    }
    public static void handle(String message, Exception e ) throws Exception {
        throw new DefaultException(message, e );
    }

    public static class DefaultException extends RuntimeException {

		private static final long serialVersionUID = -2456951510553062187L;

		public DefaultException( String message ) {
            super( message );
        }

        public DefaultException( String message, Throwable cause ) {
            super( message, cause );
        }

        public DefaultException( Throwable cause ) {
            super( "Wrapped Exception", cause );
        }
    }
}
