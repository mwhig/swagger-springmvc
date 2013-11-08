/**
 * 
 */
package com.mangofactory.swagger.models;

import com.fasterxml.classmate.ResolvedType;
import com.mangofactory.swagger.models.TypeProcessingRule;

/**
 * @author mwhig
 * 
 */
public class IgnorablePackageRule implements TypeProcessingRule {
        private ResolvedType originalType;
        public final Package ignoredPackage;

        public IgnorablePackageRule(Package ignoredPackage) {
                this.ignoredPackage = ignoredPackage;
                this.originalType = ResolvedTypes.asResolvedType(Package.class);
        }

        public IgnorablePackageRule(String ignoredPackageName) {
                this(Package.getPackage(ignoredPackageName));
        }

        @Override
        public boolean isIgnorable() {
                return true;
        }

        @Override
        public boolean hasAlternateType() {
                return false;
        }

        @Override
        public ResolvedType originalType() {
                return this.originalType;
        }

        @Override
        public ResolvedType alternateType(ResolvedType parameterType) {
                return parameterType;
        }
}
