package org.repodriller.tutorial.q4;

import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.GitRepository;
import org.repodriller.util.Constant;

// Q4: What is the lines of code growth?
public class Q4Study implements Study {

    public static void main(String[] args) {
        new RepoDriller().start(new Q4Study());
    }

    @Override
    public void execute() {
        new RepositoryMining()
                .in(GitRepository.singleProject(Constant.REPO_PATH))
                .through(Commits.monthly(6))
                .process(new LinesOfCodeVisitor(), new CSVFile(Constant.OUTPUT_PATH + "q4.csv"))
                .mine();
    }
}
