--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

-- Started on 2024-01-23 14:37:39

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 41006)
-- Name: friendships; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.friendships (
    id character varying(100) NOT NULL,
    id_user1 character varying(100),
    id_user2 character varying(100),
    friendsfrom character varying(100),
    status character varying(100)
);


ALTER TABLE public.friendships OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 90165)
-- Name: messages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.messages (
    id character varying(100) NOT NULL,
    "from" character varying(100),
    "to" character varying(100),
    message character varying(1000),
    date character varying(100),
    replyto character varying(100)
);


ALTER TABLE public.messages OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 40999)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id character varying(100) NOT NULL,
    firstname character varying(100),
    lastname character varying(100),
    email character varying(100),
    password character varying(100)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 4849 (class 0 OID 41006)
-- Dependencies: 216
-- Data for Name: friendships; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.friendships (id, id_user1, id_user2, friendsfrom, status) FROM stdin;
8d09b66d-cbed-4ad5-b66d-2679273ce2f0	4535a3b1-e428-40b4-b246-452a3085f51b	0110350e-690b-4347-8421-3a859bd9adb3	2023-12-17T01:51	ACCEPTED
7e50c67d-4d1d-4352-8a65-7f086426a5de	12ac8e9e-60ec-4f9e-9161-f71ba8fce27e	0110350e-690b-4347-8421-3a859bd9adb3	2023-12-17T01:44	ACCEPTED
a1d62909-342f-4358-b81b-a3df153c5f2a	12ac8e9e-60ec-4f9e-9161-f71ba8fce27e	4535a3b1-e428-40b4-b246-452a3085f51b	2023-12-17T01:53	PENDING
4fdc0c36-736b-4644-94dd-60df69e921e0	c6e8e605-dbe8-43b6-adaf-7ffbb5d4be97	4535a3b1-e428-40b4-b246-452a3085f51b	2023-12-17T01:55	ACCEPTED
\.


--
-- TOC entry 4850 (class 0 OID 90165)
-- Dependencies: 217
-- Data for Name: messages; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.messages (id, "from", "to", message, date, replyto) FROM stdin;
75a05213-7b6d-45fb-8df7-7115c9862be2	0110350e-690b-4347-8421-3a859bd9adb3	12ac8e9e-60ec-4f9e-9161-f71ba8fce27e	salut jarjar	2023-12-17 01:52	null
5c25474a-3279-47d7-8e4d-239f317088c4	0110350e-690b-4347-8421-3a859bd9adb3	12ac8e9e-60ec-4f9e-9161-f71ba8fce27e	iti urez una	2023-12-17 01:52	null
04617eb0-bd76-4ba2-ae65-04cf76874038	12ac8e9e-60ec-4f9e-9161-f71ba8fce27e	0110350e-690b-4347-8421-3a859bd9adb3	ce imi urezi daniel?	2023-12-17 01:52	null
ab556468-6d38-4715-9874-75bffd129655	4535a3b1-e428-40b4-b246-452a3085f51b	0110350e-690b-4347-8421-3a859bd9adb3	spune-i lui jarjar ca il fac varza tocata la borcan	2023-12-17 01:53	null
1a784af7-e25f-4de3-b22e-ac39b7452f0b	0110350e-690b-4347-8421-3a859bd9adb3	12ac8e9e-60ec-4f9e-9161-f71ba8fce27e	una :3	2023-12-17 01:54	null
18c49a78-2b55-4ef5-9e5b-400f58d9a261	0110350e-690b-4347-8421-3a859bd9adb3	12ac8e9e-60ec-4f9e-9161-f71ba8fce27e	nu intelegi?	2023-12-17 01:54	5c25474a-3279-47d7-8e4d-239f317088c4
156de962-0d7d-4063-819a-adbdafd1a019	4535a3b1-e428-40b4-b246-452a3085f51b	c6e8e605-dbe8-43b6-adaf-7ffbb5d4be97	hitmanul meu preferat :3	2023-12-17 01:56	null
834c9154-a914-4cb1-a8de-481266bcf259	12ac8e9e-60ec-4f9e-9161-f71ba8fce27e	0110350e-690b-4347-8421-3a859bd9adb3	lasa-ma in pace dani	2023-12-17 01:58	null
7e1514cc-271f-43ba-bbe2-46935ce98908	12ac8e9e-60ec-4f9e-9161-f71ba8fce27e	0110350e-690b-4347-8421-3a859bd9adb3	an nou fericit domnule dani	2023-12-31 20:42	834c9154-a914-4cb1-a8de-481266bcf259
418cce1e-8edc-42b3-90ab-06a394ec8cfa	0110350e-690b-4347-8421-3a859bd9adb3	12ac8e9e-60ec-4f9e-9161-f71ba8fce27e	an nou fericit si tie domnule jarjar	2023-12-31 20:43	null
8c6ecc81-f5ec-4e74-80ea-cdd5f526e986	0110350e-690b-4347-8421-3a859bd9adb3	12ac8e9e-60ec-4f9e-9161-f71ba8fce27e	te sarut no homo	2023-12-31 20:43	null
35dc0320-8dc9-4115-9259-20cd211cd625	12ac8e9e-60ec-4f9e-9161-f71ba8fce27e	0110350e-690b-4347-8421-3a859bd9adb3	si eu va sarut full homo domnule daniel :3	2023-12-31 20:43	8c6ecc81-f5ec-4e74-80ea-cdd5f526e986
\.


--
-- TOC entry 4848 (class 0 OID 40999)
-- Dependencies: 215
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, firstname, lastname, email, password) FROM stdin;
12ac8e9e-60ec-4f9e-9161-f71ba8fce27e	jarjar	binks	jarjarbinks@gmail.com	j8QZ4lo+ys4eByHlBS8gsw==
0110350e-690b-4347-8421-3a859bd9adb3	danny	devito	dannydevito@gmail.com	O+r4rp/jplGb7v4U4uyCOA==
4535a3b1-e428-40b4-b246-452a3085f51b	frank	castle	punisher@gmail.com	mHKjOLy04oamlv2h3hBEdw==
c6e8e605-dbe8-43b6-adaf-7ffbb5d4be97	agent	patrusapte	agent47@gmail.com	3sEZ7d4QTR05go0jJgC0IQ==
03ed90be-4558-4cd5-8b24-9a7e88e5775a	musatoiu	iulian	musi@gmail.com	2NrNid60K5mB8FAI+z3Fsw==
73f3f5ca-6ac5-457b-9829-c3db3f0f084d	user	nou	usernou@gmail.com	+HAeI8pZZEb4gfk7JcbxSg==
\.


--
-- TOC entry 4698 (class 2606 OID 41010)
-- Name: friendships friendships_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.friendships
    ADD CONSTRAINT friendships_pkey PRIMARY KEY (id);


--
-- TOC entry 4700 (class 2606 OID 90171)
-- Name: messages messages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_pkey PRIMARY KEY (id);


--
-- TOC entry 4696 (class 2606 OID 41005)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4701 (class 2606 OID 73780)
-- Name: friendships id_user1_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.friendships
    ADD CONSTRAINT id_user1_fk FOREIGN KEY (id_user1) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 4703 (class 2606 OID 90172)
-- Name: messages id_user1_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT id_user1_fk FOREIGN KEY ("from") REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 4702 (class 2606 OID 73785)
-- Name: friendships id_user2_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.friendships
    ADD CONSTRAINT id_user2_fk FOREIGN KEY (id_user2) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- TOC entry 4704 (class 2606 OID 90177)
-- Name: messages id_user2_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT id_user2_fk FOREIGN KEY ("to") REFERENCES public.users(id) ON DELETE CASCADE;


-- Completed on 2024-01-23 14:37:40

--
-- PostgreSQL database dump complete
--

