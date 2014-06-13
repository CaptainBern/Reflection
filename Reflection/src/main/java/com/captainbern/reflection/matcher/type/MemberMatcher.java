package com.captainbern.reflection.matcher.type;

import com.captainbern.reflection.matcher.AbstractMatcher;

import java.lang.reflect.Member;
import java.util.regex.Pattern;

public class MemberMatcher<T extends Member> extends AbstractMatcher<T> {

    protected int requiredMods;
    protected int bannedMods;
    protected Pattern nameRegex;

    protected MemberMatcher() {
    }

    public int getRequiredMods() {
        return this.requiredMods;
    }

    public int getBannedMods() {
        return this.bannedMods;
    }

    public Pattern getNameRegex() {
        return this.nameRegex;
    }

    @Override
    public boolean matches(Member type) {
        int mods = type.getModifiers();

        return (mods&this.requiredMods) == this.requiredMods &&
                (mods&this.bannedMods) == 0 &&
                matchName(type.getName());
    }

    private boolean matchName(String otherName) {
        if (this.nameRegex == null) {
            return true;
        } else {
            return this.nameRegex.matcher(otherName).matches();
        }
    }

    public static abstract class Builder<T extends MemberMatcher<?>> {

        protected T matcher = createMatcher();

        public Builder<T> withRequiredMod(int mod) {
            matcher.requiredMods |= mod;
            return this;
        }

        public Builder<T> withBannedMod(int mod) {
            matcher.bannedMods |= mod;
            return this;
        }

        public Builder<T> withNameRegex(Pattern pattern) {
            matcher.nameRegex = pattern;
            return this;
        }

        public Builder<T> withNameRegex(String regex) {
            return withNameRegex(Pattern.compile(regex));
        }

        public Builder<T> withExactName(String name) {
            return withNameRegex(Pattern.quote(name));
        }

        protected abstract T createMatcher();

        public abstract T build();

    }
}
