package org.repodriller.experiment.q2;

import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

public class CommitsWithBugVisitor implements CommitVisitor {

    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
        boolean containsABug = commit.getMsg().contains("bug");
        writer.write(
                commit.getHash(),
                containsABug
        );
    }

    @Override
    public String name() {
        return "commits-with-bugs";
    }
}
