// rough copy of mining a sofware repo
// with credit to Maurício Aniche, https://github.com/mauricioaniche/repodriller 
import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

public class CommitsWithBugVisitor implements CommitVisitor {

	@Override
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		// looking for the term "fix" in the commit msg
		boolean hasFix = commit.getMsg().contains("fix");
		if (hasFix)
			{
			writer.write(
					commit.getHash(),
					commit.getMsg()

				);
			}
			
	

	}
	
	@Override
	public String name() {
		return "commits-with-bugs";
	}
}

