// rough copy of MSR exercise; credit to https://github.com/mauricioaniche/repodriller

import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
//import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.GitRemoteRepository;
//import org.repodriller.scm.GitRepository;

public class MyStudy implements Study {

	public static void main(String[] args) {
		new RepoDriller().start(new MyStudy());
	}

	@Override
	public void execute() {
		
		new RepositoryMining()
		.in(GitRemoteRepository.singleProject("https://github.com/testcontainers/testcontainers-java"))
		.through(Commits.all())
		.process(new CommitsWithBugVisitor(), new CSVFile("/Users/radu/Desktop/fixcommits.csv", true))
		.mine();
	}
}
