package hudson.remoting;

final class Response<RSP,EXC extends Throwable> extends Command {
  
  private final int id;
  
  @Override
     protected void execute(Channel channel) {
         Request req = channel.pendingCalls.get(id);
         if(req==null)
             return; // maybe aborted
         req.onCompleted(this);
         channel.pendingCalls.remove(id);
     }
}
