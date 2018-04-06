package org.repodriller.experiment.q2;

import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.GitRepository;
import org.repodriller.util.Constant;

// Q2: In how many commits does the word "bug" appear?
public class Q2Study implements Study {

    public static void main(String[] args) {
        new RepoDriller().start(new Q2Study());
    }

    @Override
    public void execute() {
        String[] header = {"hash","contains_bug"};
        new RepositoryMining()
                .in(GitRepository.singleProject(Constant.REPO_PATH))
                .through(Commits.range("bf03fad60a26b27263fc5be23336eabd892f7e59", "5453efc2f0295bc4e36b161982297003da01c14f"))
                .process(new CommitsWithBugVisitor(), new CSVFile(Constant.OUTPUT_PATH + "q2.csv", header))
                .mine();
    }
}
