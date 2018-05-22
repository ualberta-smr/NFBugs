package hudson.remoting;

 @Override
    protected void execute(Channel channel) {
        Request req = channel.pendingCalls.get(id);
        if(req==null)
            return; // maybe aborted
        req.onCompleted(this);
        channel.pendingCalls.remove(id);
    }
