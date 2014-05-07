package com.captainbern.reflection.fuzzy.matchers;

import com.captainbern.reflection.matcher.AbstractMatcher;

import java.lang.reflect.Member;
import java.util.regex.Pattern;

public abstract class FuzzyMemberContext<T extends Member> extends AbstractMatcher<T> {

    public abstract static class Builder<T extends FuzzyMemberContext> {
        protected T memberContext = prepareContext();

        public Builder<T> withAccessFlags(final int accessFlags) {
            memberContext.requiredMods = accessFlags;
            return this;
        }

        public Builder<T> addRequiredAccessFlag(int flag) {
            memberContext.requiredMods |= flag;
            return this;
        }

        public Builder<T> removeRequiredAccessFlag(int flag) {
            memberContext.requiredMods = memberContext.requiredMods & ~flag;
            return this;
        }

        public Builder<T> withBannedAccessFlags(int bannedAccessFlags) {
            memberContext.bannedMods = bannedAccessFlags;
            return this;
        }

        public Builder<T> addBannedAccessFlag(final int flag) {
            memberContext.bannedMods |= flag;
            return this;
        }

        public Builder<T> removeBannedAccessFlag(final int flag) {
            memberContext.bannedMods = memberContext.bannedMods & ~flag;
            return this;
        }

        public Builder<T> withNameRegex(final String nameRegex) {
           memberContext.nameRegex = Pattern.compile(nameRegex);
            return this;
        }

        public Builder<T> withName(final String name) {
            return withNameRegex(Pattern.quote(name));
        }

        public Builder<T> withPattern(final Pattern pattern) {
            memberContext.nameRegex = pattern;
            return this;
        }

        protected abstract T prepareContext();
    }

    protected int requiredMods;
    protected int bannedMods;
    protected Pattern nameRegex;

    public int getRequiredMods() {
        return this.requiredMods;
    }

    public int getBannedMods() {
        return this.bannedMods;
    }

    public Pattern getNameRegex() {
        return this.nameRegex;
    }
}
