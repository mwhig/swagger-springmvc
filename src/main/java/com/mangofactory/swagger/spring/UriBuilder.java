package com.mangofactory.swagger.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import com.wordnik.swagger.core.DocumentationOperation;
import com.wordnik.swagger.core.DocumentationParameter;

@Slf4j
public class UriBuilder {

        private static final Pattern requestMappingURIRegex = Pattern
                        .compile("\\{([^}]*)\\}");

        private StringBuilder sb = new StringBuilder();
        boolean queryParamAdded = false;
        private boolean stripRegexFromUriParameters = true;

        public UriBuilder() {
        }

        public UriBuilder(boolean stripRegexFromUriParameters) {
                this.stripRegexFromUriParameters = stripRegexFromUriParameters;
        }

        public UriBuilder(String uri) {
                sb.append(uri);
        }

        public UriBuilder(String uri, boolean stripRegexFromUriParameters) {
                sb.append(uri);
                this.stripRegexFromUriParameters = stripRegexFromUriParameters;
        }

        public static String fromOperation(String basePath,
                        DocumentationOperation operation) {
                UriBuilder builder = new UriBuilder(basePath);
                for (DocumentationParameter parameter : operation
                                .getParameters()) {
                        if ("path".equals(parameter.getParamType())) {
                                builder.appendPath(parameter.name());
                        } else if ("query".equals(parameter.name())) {
                                builder.appendQueryString(parameter.name());
                        }
                }
                return builder.toString();
        }

        public UriBuilder appendPath(String segment) {
                if (!sb.toString().endsWith("/")) {
                        sb.append("/");
                }
                if (segment.startsWith("/")) {
                        sb.append(segment.substring(1));
                } else {
                        sb.append(segment);
                }
                return this;
        }

        private void appendQueryString(String parameterName) {
                if (queryParamAdded) {
                        sb.append(String.format("&%s={%s}", parameterName,
                                        parameterName));
                } else {
                        sb.append(String.format("?%s={%s}", parameterName,
                                        parameterName));
                }
        }

        public String toString() {
                return this.stripRegexFromUriParameters ? stripRequestMappingRegex(sb
                                .toString()) : sb.toString();
        }

        private static String stripRequestMappingRegex(final String inputUri) {
                if (inputUri == null || inputUri.isEmpty()) {
                        return inputUri;
                }
                // short-circuit pattern matching if there are no parameters.
                if (inputUri.indexOf('{') < 0) {
                        return inputUri;
                }

                String result = inputUri;
                try {
                        Matcher m = requestMappingURIRegex.matcher(inputUri);
                        String uriFormat = m.replaceAll("{%s}");
                        m.reset(); // replaceAll changes the matcher's state.
                                   // Reset before finding the matching groups.
                        List<String> paramNames = new ArrayList<String>();
                        while (m.find()) {
                                paramNames.add(m.group(1).split(":")[0]);
                        }
                        result = String.format(uriFormat, paramNames.toArray());
                        log.debug("Converted uri pattern from " + inputUri
                                        + " to " + result);
                } catch (Exception e) {
                        // Doesn't matter what exception is thrown, we should at
                        // least return the inputURI
                        log.debug("Exception while trying to strip regex from request uri: "
                                        + inputUri, e);
                }
                return result;
        }
}
