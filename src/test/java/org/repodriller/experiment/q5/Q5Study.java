package org.repodriller.experiment.q5;

import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.GitRepository;
import org.repodriller.util.Constant;

// Q5: How many additions and removals per commit are there?
public class Q5Study implements Study {

    public static void main(String[] args) {
        new RepoDriller().start(new Q5Study());
    }

    @Override
    public void execute() {
        CSVFile csv = new CSVFile(Constant.OUTPUT_PATH + "q5.csv");
        DiffVisitor visitor = new DiffVisitor();

        new RepositoryMining()
                .in(GitRepository.singleProject(Constant.REPO_PATH))
                .through(Commits.all())
                .process(visitor, csv)
                .mine();

    }
}
