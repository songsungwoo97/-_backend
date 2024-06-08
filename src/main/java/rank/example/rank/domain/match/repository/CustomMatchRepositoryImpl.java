package rank.example.rank.domain.match.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import rank.example.rank.domain.match.dto.MatchCondition;
import rank.example.rank.domain.match.entity.Match;
import rank.example.rank.domain.match.entity.QMatch;

import java.util.List;
import java.util.Objects;

@Slf4j
public class CustomMatchRepositoryImpl extends QuerydslRepositorySupport implements CustomMatchRepository {
    public CustomMatchRepositoryImpl() {
        super(Match.class);
    }

    @Override
    public Page<Match> findMatchesByCondition(MatchCondition condition, Pageable pageable) {
        QMatch qMatch = QMatch.match;
        BooleanBuilder builder = new BooleanBuilder();

        if (condition.getTier() != null) {
            builder.and(qMatch.tier.eq(condition.getTier()));
        }
        if (condition.getGender() != null && !condition.getGender().isEmpty()) {
            builder.and(qMatch.gender.eq(condition.getGender()));
        }
        if (condition.getLocation() != null && !condition.getLocation().isEmpty()) {
            builder.and(qMatch.location.eq(condition.getLocation()));
        }

        log.info("QueryDSL Predicate = {}", builder);

        JPQLQuery<Match> query = from(qMatch).where(builder);

        long total = query.fetchCount();
        List<Match> results = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();

        return new PageImpl<>(results, pageable, total);
    }
}
