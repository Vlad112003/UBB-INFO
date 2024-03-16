--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

-- Started on 2024-01-23 12:30:31

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
-- TOC entry 215 (class 1259 OID 327771)
-- Name: city; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.city (
    id character varying(100) NOT NULL,
    name character varying(100)
);


ALTER TABLE public.city OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 327801)
-- Name: ticket; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ticket (
    id character varying(100) NOT NULL,
    trainid character varying(100),
    departurecityid character varying(100),
    data date
);


ALTER TABLE public.ticket OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 327776)
-- Name: train_station; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.train_station (
    id character varying(100) NOT NULL,
    trainid character varying(100),
    departurecityid character varying(100),
    destinationcityid character varying(100)
);


ALTER TABLE public.train_station OWNER TO postgres;

--
-- TOC entry 4847 (class 0 OID 327771)
-- Dependencies: 215
-- Data for Name: city; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.city (id, name) FROM stdin;
C1	Cluj-Napoca
C2	Apahida
C3	Campia Turzii
C4	Razboieni
C5	Gilau
\.


--
-- TOC entry 4849 (class 0 OID 327801)
-- Dependencies: 217
-- Data for Name: ticket; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ticket (id, trainid, departurecityid, data) FROM stdin;
1	T5	C1	2024-01-24
2	T5	C1	2024-01-25
3	T5	C1	2024-01-22
4	T5	C1	2024-01-22
5	T3	C1	2024-01-17
6	T3	C1	2024-01-17
7	T3	C1	2024-01-17
\.


--
-- TOC entry 4848 (class 0 OID 327776)
-- Dependencies: 216
-- Data for Name: train_station; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.train_station (id, trainid, departurecityid, destinationcityid) FROM stdin;
1	T1	C1	C2
2	T1	C2	C3
3	T2	C2	C3
4	T2	C3	C4
5	T3	C3	C4
6	T4	C2	C4
7	T5	C1	C5
8	T5	C1	C3
\.


--
-- TOC entry 4696 (class 2606 OID 327775)
-- Name: city city_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.city
    ADD CONSTRAINT city_pkey PRIMARY KEY (id);


--
-- TOC entry 4700 (class 2606 OID 327805)
-- Name: ticket ticket_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT ticket_pkey PRIMARY KEY (id);


--
-- TOC entry 4698 (class 2606 OID 327780)
-- Name: train_station train_station_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.train_station
    ADD CONSTRAINT train_station_pkey PRIMARY KEY (id);


--
-- TOC entry 4703 (class 2606 OID 327806)
-- Name: ticket ticket_departurecityid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT ticket_departurecityid_fkey FOREIGN KEY (departurecityid) REFERENCES public.city(id);


--
-- TOC entry 4701 (class 2606 OID 327781)
-- Name: train_station train_station_departurecityid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.train_station
    ADD CONSTRAINT train_station_departurecityid_fkey FOREIGN KEY (departurecityid) REFERENCES public.city(id);


--
-- TOC entry 4702 (class 2606 OID 327786)
-- Name: train_station train_station_destinationcityid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.train_station
    ADD CONSTRAINT train_station_destinationcityid_fkey FOREIGN KEY (destinationcityid) REFERENCES public.city(id);


-- Completed on 2024-01-23 12:30:32

--
-- PostgreSQL database dump complete
--

