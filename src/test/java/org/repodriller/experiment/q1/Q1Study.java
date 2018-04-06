package org.repodriller.experiment.q1;

import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.GitRepository;
import org.repodriller.util.Constant;

// Q1: Who are the committers?
public class Q1Study implements Study {

    public static void main(String[] args) {
        new RepoDriller().start(new Q1Study());
    }

    @Override
    public void execute() {
        String[] header = {"hash","author_name","author_email"};
        new RepositoryMining()
                .in(GitRepository.singleProject(Constant.REPO_PATH))
                .through(Commits.all())
                .process(new DevelopersVisitor(), new CSVFile(Constant.OUTPUT_PATH + "q1.csv", header))
                .mine();
    }
}
